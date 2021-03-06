import { Alert, Dialog, DialogContent, Stack } from '@mui/material';
import { PrettyRoute } from './pretty-route';
import { Route } from '../../store/routes.store';
import { RoutesApi } from '../../api/routes.api';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import DialogTitle from '@mui/material/DialogTitle';
import React, { useState } from 'react';
import TextField from '@mui/material/TextField';

type DeleteWithDistancePopupProps = {
  isOpen: boolean;
  onClose: () => void;
};

export const DeleteWithDistancePopup: React.FC<DeleteWithDistancePopupProps> =
  ({ isOpen, onClose }) => {
    const [distance, setDistance] = useState<number>();
    const [route, setRoute] = useState<Route>();
    const [error, setError] = useState();

    const onSubmit = (distance: number) => {
      RoutesApi.deleteWithDistance(distance)
        .then(res => {
          setRoute(res);
          setError(undefined);
        })
        .catch(error => setError(error));
    };

    return (
      <Dialog open={isOpen} onClose={onClose}>
        <DialogTitle>Delete Route with Distance = ...</DialogTitle>
        <DialogContent>
          <Stack spacing={1}>
            <TextField
              value={distance || ''}
              onChange={e => setDistance(+e.target.value)}
              fullWidth
              type="number"
              size="small"
              label="Distance"
              required
            />
            {route && (
              <Alert severity="success">
                <div>Deleted route:</div>
                <PrettyRoute route={route} />
              </Alert>
            )}
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
