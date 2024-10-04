import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import Navbar from './Navbar';
import SearchResults from './SearchResults';
import TrackedItems from './TrackedItems';
import { motion, AnimatePresence } from 'framer-motion';
import { TrendingUp, ArrowLeft, Search } from 'lucide-react';

const Dashboard: React.FC = () => {
    const { currentUser, userLoggedIn, loading } = useAuth();
    const [searchResults, setSearchResults] = useState<any[]>([]);
    const [trackedItems, setTrackedItems] = useState<any[]>([]);
    const [isSearchActive, setIsSearchActive] = useState(false);
    const [searchTerm, setSearchTerm] = useState('');

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
            setIsSearchActive(true);
            setSearchTerm(item);
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

    const handleCloseSearch = () => {
        setIsSearchActive(false);
    };

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-100 flex items-center justify-center">
                <motion.div 
                    className="w-16 h-16 border-4 border-gray-300 border-t-blue-500 rounded-full"
                    animate={{ rotate: 360 }}
                    transition={{ duration: 1, repeat: Infinity, ease: "linear" }}
                />
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-100 text-gray-900 font-sans">
            <Navbar onSearch={handleSearch} setIsSearchActive={setIsSearchActive} />
            <AnimatePresence mode="wait">
                {!isSearchActive ? (
                    <motion.div
                        key="dashboard"
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        exit={{ opacity: 0 }}
                        transition={{ duration: 0.3 }}
                        className="pt-24 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto"
                    >
                        <motion.div 
                            initial={{ opacity: 0, y: -50 }}
                            animate={{ opacity: 1, y: 0 }}
                            transition={{ duration: 0.5 }}
                            className="text-center mb-16"
                        >
                            <h1 className="text-7xl font-black mb-2 tracking-tighter" style={{ fontFamily: "'Caveat', cursive" }}>
                                GOAT<span className="text-blue-500">TRACKER</span>
                            </h1>
                            <p className="text-xl text-gray-600 font-light tracking-widest uppercase" style={{ fontFamily: "'Roboto Mono', monospace" }}>
                                "Elevate Your Streetwear Game"
                            </p>
                        </motion.div>
                        
                        {userLoggedIn && currentUser ? (
                            <div>
                                <motion.div 
                                    initial={{ opacity: 0 }}
                                    animate={{ opacity: 1 }}
                                    transition={{ delay: 0.3, duration: 0.5 }}
                                    className="mb-12 bg-white p-8 rounded-lg shadow-md"
                                >
                                    <h2 className="text-3xl font-bold mb-6 flex items-center" style={{ fontFamily: "'Caveat', cursive" }}>
                                        <TrendingUp className="mr-2 text-blue-500" />
                                        Dashboard
                                    </h2>
                                    <p className="text-xl mb-6 font-mono">Welcome back, <span className="font-semibold text-blue-500">{currentUser.displayName || currentUser.email || 'User'}</span></p>
                                    <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                                        {['Tracked Items', 'Total Savings', 'Alerts'].map((item, index) => (
                                            <motion.div 
                                                key={item}
                                                className="bg-gray-50 p-6 rounded-md shadow-inner"
                                                initial={{ opacity: 0, y: 20 }}
                                                animate={{ opacity: 1, y: 0 }}
                                                transition={{ delay: 0.4 + index * 0.1, duration: 0.3 }}
                                            >
                                                <h3 className="text-lg font-semibold mb-2 text-gray-600 font-mono uppercase">{item}</h3>
                                                <p className="text-4xl font-bold text-blue-500">
                                                    {index === 0 ? trackedItems.length : index === 1 ? '$230' : '3'}
                                                </p>
                                            </motion.div>
                                        ))}
                                    </div>
                                </motion.div>
                                
                                <div className="bg-white p-8 rounded-lg shadow-md">
                                    <h2 className="text-2xl font-bold mb-6">Tracked Items</h2>
                                    <TrackedItems items={trackedItems} />
                                </div>
                            </div>
                        ) : (
                            <motion.div 
                                initial={{ opacity: 0 }}
                                animate={{ opacity: 1 }}
                                transition={{ delay: 0.3, duration: 0.5 }}
                                className="text-center bg-white p-12 rounded-lg shadow-md"
                            >
                                <p className="text-xl font-medium mb-8 font-mono">Sign in to access your GOATTRACKER dashboard and elevate your streetwear game.</p>
                                <motion.button 
                                    className="bg-blue-500 text-white px-10 py-4 rounded-full font-bold text-lg hover:bg-blue-600 transition-colors duration-300 flex items-center justify-center mx-auto"
                                    whileHover={{ scale: 1.05 }}
                                    whileTap={{ scale: 0.95 }}
                                >
                                    SIGN IN
                                </motion.button>
                            </motion.div>
                        )}
                    </motion.div>
                ) : (
                    <motion.div
                        key="search"
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        exit={{ opacity: 0 }}
                        transition={{ duration: 0.3 }}
                        className="pt-24 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto"
                    >
                        <div className="bg-white p-8 rounded-lg shadow-md">
                            <div className="flex justify-between items-center mb-6">
                                <h2 className="text-3xl font-bold flex items-center">
                                    <Search className="mr-2 text-blue-500" />
                                    Search Results for "{searchTerm}"
                                </h2>
                                <button
                                    onClick={handleCloseSearch}
                                    className="text-gray-500 hover:text-gray-700 transition-colors flex items-center"
                                >
                                    <ArrowLeft size={24} className="mr-2" />
                                    Back to Dashboard
                                </button>
                            </div>
                            <SearchResults results={searchResults} onTrackItem={handleTrackItem} />
                        </div>
                        <div className="mt-8 bg-white p-8 rounded-lg shadow-md">
                            <h2 className="text-2xl font-bold mb-6">Your Tracked Items</h2>
                            <TrackedItems items={trackedItems} />
                        </div>
                    </motion.div>
                )}
            </AnimatePresence>
        </div>
    );
};

export default Dashboard;