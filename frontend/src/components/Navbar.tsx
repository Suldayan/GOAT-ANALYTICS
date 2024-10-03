import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { Search, Home, User, Menu } from 'lucide-react';
import { Link } from 'react-router-dom';

interface NavbarProps {
  onSearch: (item: string, count: number) => void;
}

const Navbar: React.FC<NavbarProps> = ({ onSearch }) => {
  const [searchOpen, setSearchOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [itemCount, setItemCount] = useState(20);
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch(searchTerm, itemCount);
    setSearchOpen(false);
  };

  return (
    <nav className="bg-white text-black py-4 px-6 fixed w-full top-0 left-0 z-50 shadow-md">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold tracking-wider">GOAT</Link>
        
        <div className="hidden md:flex items-center space-x-8">
          <NavLink to="/">DASHBOARD</NavLink>
          <NavLink to="/profile">PROFILE</NavLink>
          <motion.button
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.9 }}
            onClick={() => setSearchOpen(!searchOpen)}
            className="focus:outline-none"
          >
            <Search size={24} />
          </motion.button>
        </div>

        <button
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          className="md:hidden focus:outline-none"
        >
          <Menu size={24} />
        </button>
      </div>

      {/* Mobile Menu */}
      {mobileMenuOpen && (
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: -20 }}
          className="md:hidden bg-white absolute top-full left-0 right-0 py-4 px-6 shadow-md"
        >
          <NavLink to="/" mobile>DASHBOARD</NavLink>
          <NavLink to="/profile" mobile>PROFILE</NavLink>
          <button
            onClick={() => setSearchOpen(!searchOpen)}
            className="block w-full text-left py-2 focus:outline-none"
          >
            SEARCH
          </button>
        </motion.div>
      )}

      {/* Search Overlay */}
      {searchOpen && (
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: -20 }}
          className="absolute top-full left-0 right-0 bg-white p-4 shadow-md"
        >
          <form onSubmit={handleSearchSubmit} className="flex items-center">
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Search items..."
              className="flex-grow bg-gray-100 px-4 py-2 rounded-l focus:outline-none"
            />
            <input
              type="number"
              value={itemCount}
              onChange={(e) => setItemCount(parseInt(e.target.value))}
              min="1"
              max="100"
              className="w-20 bg-gray-100 px-2 py-2 focus:outline-none"
            />
            <button
              type="submit"
              className="bg-black text-white px-4 py-2 rounded-r focus:outline-none"
            >
              SEARCH
            </button>
          </form>
        </motion.div>
      )}
    </nav>
  );
};

const NavLink: React.FC<{ to: string; children: React.ReactNode; mobile?: boolean }> = ({ to, children, mobile }) => (
  <Link
    to={to}
    className={`${
      mobile ? 'block py-2' : 'inline-block'
    } font-medium tracking-wider hover:text-gray-600 transition-colors`}
  >
    {children}
  </Link>
);

export default Navbar;