import { Alert, Dialog, DialogContent, Stack } from '@mui/material';
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
      RoutesApi.getRouteById(distance)
        .then(res => setRoute(res))
        .catch(error => setError(error));
    };

    return (
      <Dialog open={isOpen} onClose={onClose}>
        <DialogTitle>Delete Route with Distance = ...</DialogTitle>
        <DialogContent>
          <Stack>
            <TextField
              value={distance}
              onChange={e => setDistance(parseInt(e.target.value, 10))}
              fullWidth
              type="number"
              size="small"
              label="Distance"
              required
            />
            {route && (
              <Alert severity="success">
                <div>Deleted route:</div>
                <code>{JSON.stringify(route)}</code>
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
