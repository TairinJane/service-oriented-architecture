import { DeleteWithDistancePopup } from '../popups/delete-with-distance-popup';
import Button from '@mui/material/Button';
import React, { useState } from 'react';

export const DeleteWithDistanceButton: React.FC = () => {
  const [isOpen, setOpen] = useState(false);

  return (
    <>
      <Button
        variant="contained"
        color="secondary"
        fullWidth
        onClick={() => setOpen(true)}
      >
        Delete Route with Distance = ...
      </Button>
      {isOpen && (
        <DeleteWithDistancePopup
          isOpen={isOpen}
          onClose={() => setOpen(false)}
        />
      )}
    </>
  );
};
