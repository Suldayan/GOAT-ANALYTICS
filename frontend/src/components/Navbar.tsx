import React from 'react'

const Navbar: React.FC = () => {
    return (
        <div>
            <nav className=''>
                <ul>
                    <li>
                        <h3>SEARCH</h3>
                    </li>
                    <li>
                        <h3>DASHBOARD</h3>
                    </li>
                    <li>
                        <h3>PROFILE</h3>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default Navbar