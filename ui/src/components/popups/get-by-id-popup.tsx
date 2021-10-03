import { Alert, Dialog, DialogContent, Stack } from '@mui/material';
import { PrettyRoute } from './pretty-route';
import { Route } from '../../store/routes.store';
import { RoutesApi } from '../../api/routes.api';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import DialogTitle from '@mui/material/DialogTitle';
import React, { useState } from 'react';
import TextField from '@mui/material/TextField';

type GetByIdPopupProps = {
  isOpen: boolean;
  onClose: () => void;
};

export const GetByIdPopup: React.FC<GetByIdPopupProps> = ({
  isOpen,
  onClose,
}) => {
  const [id, setId] = useState<number>();
  const [route, setRoute] = useState<Route>();
  const [error, setError] = useState();

  const onSubmit = (id: number) => {
    RoutesApi.getRouteById(id)
      .then(res => setRoute(res))
      .catch(error => setError(error));
  };

  return (
    <Dialog open={isOpen} onClose={onClose}>
      <DialogTitle>Get Route by Id</DialogTitle>
      <DialogContent>
        <Stack spacing={1}>
          <TextField
            value={id || ''}
            onChange={e => setId(parseInt(e.target.value, 10))}
            fullWidth
            type="number"
            size="small"
            label="Id"
            required
          />
          {route && (
            <Alert severity="info">
              <PrettyRoute route={route} />
            </Alert>
          )}
          {error && <Alert severity="error">{error}</Alert>}
        </Stack>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button
          onClick={() => id != undefined && onSubmit(id)}
          disabled={id == undefined}
        >
          Submit
        </Button>
      </DialogActions>
    </Dialog>
  );
};
