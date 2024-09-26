import React, { useState, FormEvent } from 'react';
import { motion } from 'framer-motion';
import { auth } from '../firebaseConfig';
import { sendSignInLinkToEmail, GoogleAuthProvider, signInWithPopup, UserCredential } from 'firebase/auth';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login: React.FC = () => {
    const [email, setEmail] = useState<string>('');
    const [notice, setNotice] = useState<string>('');
    const navigate = useNavigate();

    const actionCodeSettings = {
        url: "http://localhost:3000/confirm",
        handleCodeInApp: true
    };

    const callSendSignInLinkToEmail = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        sendSignInLinkToEmail(auth, email, actionCodeSettings)
            .then(() => {
                setNotice("An email has been sent. Click the link to login.");
                window.localStorage.setItem('emailForSignIn', email);
            })
            .catch((error) => {
                setNotice(`Error sending email: ${error.message}`);
            });
    };

    const doSignInWithGoogle = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        const provider = new GoogleAuthProvider();

        try {
            const result: UserCredential = await signInWithPopup(auth, provider);
            console.log("Google sign-in successful", result.user);
            navigate('/dashboard');

            // TO-DO: send the user data into the backend to save into the database
            // axios.post(credentials, localhost:8080)
        } catch (error) {
            setNotice(`Google sign-in failed: ${(error as Error).message}`);
        }
    };

    return (
        <div className="min-h-screen bg-black flex items-center justify-center px-4 sm:px-6 lg:px-8">
            <motion.div 
                initial={{ opacity: 0, y: -50 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.5 }}
                className="max-w-md w-full space-y-8"
            >
                <div>
                    <h1 className="mt-6 text-center text-5xl font-bold text-white tracking-tight">
                        GOAT
                    </h1>
                    <h2 className="mt-2 text-center text-xl text-gray-400 uppercase tracking-widest">
                        Analytics Login
                    </h2>
                </div>
                <form className="mt-8 space-y-6" onSubmit={callSendSignInLinkToEmail}>
                    <div className="rounded-md shadow-sm -space-y-px">
                        <div>
                            <label htmlFor="email-address" className="sr-only">Email address</label>
                            <input
                                id="email-address"
                                name="email"
                                type="email"
                                autoComplete="email"
                                required
                                className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-700 placeholder-gray-500 text-white bg-gray-900 focus:outline-none focus:ring-white focus:border-white focus:z-10 sm:text-sm"
                                placeholder="Email address"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>
                    </div>

                    {notice && (
                    <motion.div
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        className="bg-gray-800 border border-gray-700 text-white px-4 py-3 rounded relative"
                        role="alert"
                    >
                        <span className="block sm:inline">{notice}</span>
                    </motion.div>
                )}

                    <div className="flex flex-col space-y-4">
                        <motion.button
                            whileHover={{ scale: 1.05 }}
                            whileTap={{ scale: 0.95 }}
                            type="submit"
                            className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium text-black bg-white hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-white transition-colors duration-200"
                        >
                            Sign in with Email
                        </motion.button>
                        
                        <motion.button
                            whileHover={{ scale: 1.05 }}
                            whileTap={{ scale: 0.95 }}
                            onClick={doSignInWithGoogle}
                            className="group relative w-full flex justify-center py-2 px-4 border border-white text-sm font-medium text-white bg-black hover:bg-gray-900 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-white transition-colors duration-200"
                        >
                            Sign in with Google
                        </motion.button>
                    </div>
                </form>
                
                <p className="mt-2 text-center text-sm text-gray-600">
                    By signing in, you agree to GOAT's{' '}
                    <a href="#" className="font-medium text-white hover:text-gray-200">
                        Terms of Service
                    </a>{' '}
                    and{' '}
                    <a href="#" className="font-medium text-white hover:text-gray-200">
                        Privacy Policy
                    </a>
                </p>
            </motion.div>
        </div>
    );
};

export default Login;