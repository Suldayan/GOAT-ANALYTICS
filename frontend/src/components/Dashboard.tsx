import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import Navbar from './Navbar';
import SearchResults from './SearchResults';
import TrackedItems from './TrackedItems';
import { motion } from 'framer-motion';
import { Search, Package } from 'lucide-react';

const Dashboard: React.FC = () => {
    const { currentUser, userLoggedIn, loading } = useAuth();
    const [searchResults, setSearchResults] = useState<any[]>([]);
    const [trackedItems, setTrackedItems] = useState<any[]>([]);

    useEffect(() => {
        if (userLoggedIn && currentUser) {
            fetchTrackedItems();
        }
    }, [userLoggedIn, currentUser]);

    const handleSearch = async (item: string, count: number) => {
        try {
            const response = await fetch(`http://localhost:8080/scrape/items?item=${encodeURIComponent(item)}&itemCount=${count}`);
            const data = await response.json();
            setSearchResults(data);
        } catch (error) {
            console.error('Error fetching search results:', error);
        }
    };

    const fetchTrackedItems = async () => {
        const mockTrackedItems = [
            { id: '1', name: 'Nike Air Max', currentPrice: 120, initialPrice: 150 },
            { id: '2', name: 'Adidas Ultra Boost', currentPrice: 180, initialPrice: 200 },
        ];
        setTrackedItems(mockTrackedItems);
    };

    const handleTrackItem = async (item: any) => {
        setTrackedItems([...trackedItems, item]);
    };

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-50 flex items-center justify-center">
                <div className="w-12 h-12 border-4 border-red-500 rounded-full animate-spin border-t-transparent"></div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-50 text-gray-900 font-sans">
            <Navbar onSearch={handleSearch} />
            <div className="pt-24 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
                <motion.h1 
                    initial={{ opacity: 0, y: -50 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.5 }}
                    className="text-5xl font-bold mb-12 text-center tracking-tight"
                >
                    GOAT<span className="text-red-500">TRACKER</span>
                </motion.h1>
                {userLoggedIn && currentUser ? (
                    <div>
                        <motion.p 
                            initial={{ opacity: 0 }}
                            animate={{ opacity: 1 }}
                            transition={{ delay: 0.3, duration: 0.5 }}
                            className="text-xl mb-12 text-center font-medium"
                        >
                            Welcome back, {currentUser.displayName || currentUser.email || 'User'}
                        </motion.p>
                        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                            <motion.div
                                initial={{ opacity: 0, x: -20 }}
                                animate={{ opacity: 1, x: 0 }}
                                transition={{ delay: 0.4, duration: 0.5 }}
                                className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300"
                            >
                                <h2 className="text-2xl font-bold mb-6 flex items-center">
                                    <Package className="mr-2 text-red-500" />
                                    TRACKED ITEMS
                                </h2>
                                <TrackedItems items={trackedItems} />
                            </motion.div>
                            <motion.div
                                initial={{ opacity: 0, x: 20 }}
                                animate={{ opacity: 1, x: 0 }}
                                transition={{ delay: 0.4, duration: 0.5 }}
                                className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300"
                            >
                                <h2 className="text-2xl font-bold mb-6 flex items-center">
                                    <Search className="mr-2 text-red-500" />
                                    SEARCH RESULTS
                                </h2>
                                <SearchResults results={searchResults} onTrackItem={handleTrackItem} />
                            </motion.div>
                        </div>
                    </div>
                ) : (
                    <motion.div 
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        transition={{ delay: 0.3, duration: 0.5 }}
                        className="text-center"
                    >
                        <p className="text-xl font-medium mb-4">Please sign in to access your Stüssy Tracker dashboard.</p>
                        <button className="bg-red-500 text-white px-6 py-2 rounded-full font-bold hover:bg-red-600 transition-colors duration-300">
                            SIGN IN
                        </button>
                    </motion.div>
                )}
            </div>
        </div>
    );
};

export default Dashboard;