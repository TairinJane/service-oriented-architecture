import { GetByIdPopup } from '../popups/get-by-id-popup';
import Button from '@mui/material/Button';
import React, { useState } from 'react';

export const GetByIdButton: React.FC = () => {
  const [isOpen, setOpen] = useState(false);

  return (
    <>
      <Button
        variant="contained"
        color="secondary"
        fullWidth
        onClick={() => setOpen(true)}
      >
        Get Route by ID
      </Button>
      {isOpen && (
        <GetByIdPopup isOpen={isOpen} onClose={() => setOpen(false)} />
      )}
    </>
  );
};
