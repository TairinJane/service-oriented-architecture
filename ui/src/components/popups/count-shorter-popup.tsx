import { Alert, Dialog, DialogContent, Stack } from '@mui/material';
import { RoutesApi } from '../../api/routes.api';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import DialogTitle from '@mui/material/DialogTitle';
import React, { useState } from 'react';
import TextField from '@mui/material/TextField';

type CountShorterPopupProps = {
  isOpen: boolean;
  onClose: () => void;
};

export const CountShorterPopup: React.FC<CountShorterPopupProps> = ({
  isOpen,
  onClose,
}) => {
  const [distance, setDistance] = useState<number>();
  const [count, setCount] = useState<number>();
  const [error, setError] = useState();

  const onSubmit = (distance: number) => {
    RoutesApi.countShorterThan(distance)
      .then(res => setCount(res))
      .catch(error => setError(error));
  };

  return (
    <Dialog open={isOpen} onClose={onClose}>
      <DialogTitle>Count Routes shorter than ...</DialogTitle>
      <DialogContent>
        <Stack spacing={1}>
          <TextField
            value={distance || ''}
            onChange={e => setDistance(parseInt(e.target.value, 10))}
            fullWidth
            type="number"
            size="small"
            label="Distance"
            required
          />
          {count && <Alert severity="success">Count: {count}</Alert>}
          {error && <Alert severity="error">{error}</Alert>}
        </Stack>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button
          onClick={() => distance != undefined && onSubmit(distance)}
          disabled={distance == undefined}
        >
          Submit
        </Button>
      </DialogActions>
    </Dialog>
  );
};
