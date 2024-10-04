import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Search, Menu } from 'lucide-react';
import { Link } from 'react-router-dom';

interface NavbarProps {
  onSearch: (item: string, count: number) => void;
  setIsSearchActive: (isActive: boolean) => void;
}

const Navbar: React.FC<NavbarProps> = ({ onSearch, setIsSearchActive }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [itemCount, setItemCount] = useState(20);
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchTerm.trim()) {
      onSearch(searchTerm, itemCount);
      setIsSearchActive(true);
    }
  };

  return (
    <nav className="bg-white text-black py-4 px-6 fixed w-full top-0 left-0 z-50 shadow-md">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold tracking-wider">GOAT</Link>
        
        <div className="hidden md:flex items-center space-x-8">
          <NavLink to="/">DASHBOARD</NavLink>
          <NavLink to="/profile">PROFILE</NavLink>
          <form onSubmit={handleSearchSubmit} className="flex items-center">
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Search items..."
              className="bg-gray-100 px-4 py-2 rounded-l focus:outline-none"
            />
            <input
              type="number"
              value={itemCount}
              onChange={(e) => setItemCount(parseInt(e.target.value))}
              min="1"
              max="100"
              className="w-20 bg-gray-100 px-2 py-2 focus:outline-none"
            />
            <motion.button
              type="submit"
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              className="bg-black text-white px-4 py-2 rounded-r focus:outline-none"
            >
              <Search size={20} />
            </motion.button>
          </form>
        </div>

        <button
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          className="md:hidden focus:outline-none"
        >
          <Menu size={24} />
        </button>
      </div>

      {/* Mobile Menu */}
      <AnimatePresence>
        {mobileMenuOpen && (
          <motion.div
            initial={{ opacity: 0, y: -20 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -20 }}
            className="md:hidden bg-white absolute top-full left-0 right-0 py-4 px-6 shadow-md"
          >
            <NavLink to="/" mobile>DASHBOARD</NavLink>
            <NavLink to="/profile" mobile>PROFILE</NavLink>
            <form onSubmit={handleSearchSubmit} className="mt-4">
              <input
                type="text"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                placeholder="Search items..."
                className="w-full bg-gray-100 px-4 py-2 rounded-t focus:outline-none"
              />
              <div className="flex">
                <input
                  type="number"
                  value={itemCount}
                  onChange={(e) => setItemCount(parseInt(e.target.value))}
                  min="1"
                  max="100"
                  className="w-1/3 bg-gray-100 px-2 py-2 focus:outline-none"
                />
                <button
                  type="submit"
                  className="w-2/3 bg-black text-white px-4 py-2 rounded-b focus:outline-none"
                >
                  SEARCH
                </button>
              </div>
            </form>
          </motion.div>
        )}
      </AnimatePresence>
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