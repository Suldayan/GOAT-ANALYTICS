import React from 'react';
import { motion } from 'framer-motion';
import { PlusCircle } from 'lucide-react';

export interface SearchResult {
  id: string;
  name: string;
  price: number;
  retailPrice: number;
  image: string;
  description: string;
}

interface SearchResultsProps {
  results: SearchResult[];
  onTrackItem: (item: SearchResult) => void | Promise<void>;
}

const SearchResults: React.FC<SearchResultsProps> = ({ results, onTrackItem }) => {
  return (
    <div className="bg-gray-100 p-6 rounded-lg shadow-md">
      {results.length === 0 ? (
        <p className="text-gray-600">No results currently. Try searching for an item!</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {results.map((item) => (
            <motion.div
              key={item.id}
              whileHover={{ scale: 1.03 }}
              whileTap={{ scale: 0.98 }}
              className="bg-white shadow-sm rounded-lg overflow-hidden"
            >
              <img src={item.image} alt={item.name} className="w-full h-48 object-cover" />
              <div className="p-4">
                <h3 className="text-lg font-semibold mb-2 uppercase">{item.name}</h3>
                <div className="flex justify-between items-center">
                  <p className="text-gray-600">${item.price.toFixed(2)}</p>
                  <button
                    onClick={() => onTrackItem(item)}
                    className="text-blue-500 hover:text-blue-600 focus:outline-none"
                  >
                    <PlusCircle size={24} />
                  </button>
                </div>
              </div>
            </motion.div>
          ))}
        </div>
      )}
    </div>
  );
};

export default SearchResults;