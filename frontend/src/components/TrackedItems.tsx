import React from 'react';
import { motion } from 'framer-motion';
import { TrendingUp, TrendingDown } from 'lucide-react';

interface TrackedItem {
  id: string;
  name: string;
  currentPrice: number;
  initialPrice: number;
}

interface TrackedItemsProps {
  items: TrackedItem[];
}

const TrackedItems: React.FC<TrackedItemsProps> = ({ items }) => {
  return (
    <div className="bg-gray-100 p-6 rounded-lg shadow-md">
      {items.length === 0 ? (
        <p className="text-gray-600">No items currently tracked.</p>
      ) : (
        <ul className="space-y-4">
          {items.map((item) => (
            <motion.li
              key={item.id}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0, y: -20 }}
              transition={{ duration: 0.3 }}
              className="bg-white p-4 rounded-md shadow-sm"
            >
              <h3 className="text-lg font-semibold mb-2 uppercase">{item.name}</h3>
              <div className="flex justify-between items-center">
                <p className="text-gray-600">Current: ${item.currentPrice.toFixed(2)}</p>
                <p className="text-gray-600">Initial: ${item.initialPrice.toFixed(2)}</p>
                {item.currentPrice < item.initialPrice ? (
                  <span className="text-green-500 flex items-center">
                    <TrendingDown size={20} className="mr-1" />
                    {((item.initialPrice - item.currentPrice) / item.initialPrice * 100).toFixed(2)}%
                  </span>
                ) : (
                  <span className="text-red-500 flex items-center">
                    <TrendingUp size={20} className="mr-1" />
                    {((item.currentPrice - item.initialPrice) / item.initialPrice * 100).toFixed(2)}%
                  </span>
                )}
              </div>
            </motion.li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default TrackedItems;