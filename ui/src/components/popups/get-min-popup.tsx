import { Alert, Dialog, DialogContent } from '@mui/material';
import { Route } from '../../store/routes.store';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import DialogTitle from '@mui/material/DialogTitle';
import React from 'react';

type GetMinPopupProps = {
  isOpen: boolean;
  onClose: () => void;
  route?: Route;
  error?: any;
};

export const GetMinPopup: React.FC<GetMinPopupProps> = ({
  isOpen,
  onClose,
  route,
  error,
}) => {
  return (
    <Dialog open={isOpen} onClose={onClose}>
      <DialogTitle>Route with Min Distance</DialogTitle>
      <DialogContent>
        {route && (
          <Alert severity="success">
            <code>{JSON.stringify(route)}</code>
          </Alert>
        )}
        {error && <Alert severity="error">{error}</Alert>}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Close</Button>
      </DialogActions>
    </Dialog>
  );
};
