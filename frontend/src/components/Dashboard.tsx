import React from 'react';
import { useAuth } from '../contexts/AuthContext';

const Dashboard: React.FC = () => {
    const { currentUser, userLoggedIn, loading } = useAuth();

    if (loading) {
        return (
            <div className="min-h-screen bg-black text-white flex items-center justify-center">
                <p className="text-xl">Loading...</p>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-black text-white flex flex-col items-center justify-center px-4 sm:px-6 lg:px-8">
            <h1 className="text-4xl font-bold mb-8">SSENSE Analytics Dashboard</h1>
            {userLoggedIn && currentUser ? (
                <div className="text-center">
                    <p className="text-2xl mb-4">
                        Hello, {currentUser.displayName || currentUser.email || 'User'}
                    </p>
                    <p className="text-xl">Welcome to your analytics dashboard.</p>
                    {/* Add your dashboard content here */}
                </div>
            ) : (
                <p className="text-xl">Please sign in to view your dashboard.</p>
            )}
        </div>
    );
};

export default Dashboard;