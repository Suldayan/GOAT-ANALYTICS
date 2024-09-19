import React, { useState } from 'react'
import { auth } from '../firebaseConfig'
import { isSignInWithEmailLink, signInWithEmailLink } from 'firebase/auth'
import { useNavigate } from 'react-router-dom'

const Confirm = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('')
    const [notice, setNotice] = useState('')

    const callSignInWithEmailLink = (e: any) => {
        e.preventDefault();

        if (isSignInWithEmailLink(auth, window.location.href)) {
            signInWithEmailLink(auth, email, window.location.href)
            .then(() => {
                navigate('/dashboard')
            }).catch((error) => {
                setNotice('An error has occured during sign in: ' + error.name)
            })
        }
    }

    return (
        <div className = "container">
            <div className = "row justify-content-center">
                <form className = "col-md-4 mt-3 pt-3 pb-3">
                    { "" !== notice &&
                        <div className = "alert alert-warning" role = "alert">
                            { notice }    
                        </div>
                    }                  
                    <div className = "form-floating mb-3">
                        <input type = "email" className = "form-control" id = "exampleConfirmEmail" placeholder = "name@example.com" value = { email } onChange = { (e) => setEmail(e.target.value) }></input>
                        <label htmlFor = "exampleConfirmEmail" className = "form-label">Please confirm your email address</label>
                    </div>
                    <div className = "d-grid">
                        <button type = "submit" className = "btn btn-primary pt-3 pb-3" onClick = {(e) => callSignInWithEmailLink(e)}>Confirm</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Confirm