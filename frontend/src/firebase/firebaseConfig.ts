// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth, GoogleAuthProvider } from "firebase/auth";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyDumRhiIJZ8Thu96fBlZk8GC-sL9TzqwrA",
  authDomain: "ssense-auth-bdf9f.firebaseapp.com",
  projectId: "ssense-auth-bdf9f",
  storageBucket: "ssense-auth-bdf9f.appspot.com",
  messagingSenderId: "414709660567",
  appId: "1:414709660567:web:2e1e13f66dea4ae41222d2",
  measurementId: "G-67X7V5TTX8"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
export const provider = new GoogleAuthProvider();


const analytics = getAnalytics(app);