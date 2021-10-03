import { CountShorterPopup } from '../popups/count-shorter-popup';
import Button from '@mui/material/Button';
import React, { useState } from 'react';

export const CountShorterButton: React.FC = () => {
  const [isOpen, setOpen] = useState(false);

  return (
    <>
      <Button
        variant="contained"
        color="secondary"
        fullWidth
        onClick={() => setOpen(true)}
      >
        Count Routes shorter than ...
      </Button>
      {isOpen && (
        <CountShorterPopup isOpen={isOpen} onClose={() => setOpen(false)} />
      )}
    </>
  );
};
