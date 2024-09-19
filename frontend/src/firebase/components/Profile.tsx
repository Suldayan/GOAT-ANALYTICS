import React from 'react'
import { auth } from '../firebaseConfig'
import { signOut } from 'firebase/auth'
import { useNavigate } from 'react-router-dom'

const Profile = () => {
    const navigate = useNavigate();

    const logoutUser = async (e: any) => {
        e.preventDefault();

        await signOut(auth);
        navigate('/');
    }

    return (
        <div className='container'>

        </div>
    )
}

export default Profile