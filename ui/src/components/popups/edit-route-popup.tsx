import * as React from 'react';
import { RoutePartial } from '../../store/routes.store';
import { Stack } from '@mui/material';
import { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';

type EditRoutePopupProps = {
  route: RoutePartial;
  isOpen: boolean;
  onSubmit: (route: RoutePartial) => void;
  onClose: () => void;
};

export const EditRoutePopup: React.FC<EditRoutePopupProps> = ({
  route,
  isOpen,
  onSubmit,
  onClose,
}) => {
  const [routeEdit, setRouteEdit] = useState(route);

  return (
    <Dialog open={isOpen} onClose={onClose}>
      <DialogTitle>{routeEdit.id ? 'Edit' : 'Add'} Route</DialogTitle>
      <DialogContent>
        <Stack spacing={1}>
          {routeEdit.id && <div>ID: {routeEdit.id}</div>}
          <div>Name</div>
          <TextField
            value={routeEdit.name || ''}
            onChange={e =>
              setRouteEdit(route => ({ ...route, name: e.target.value }))
            }
            fullWidth
            size="small"
            label="Name"
            required
          />
          <div>Coordinates</div>
          <Stack direction="row" alignItems="center" spacing={0.5}>
            {'('}
            <TextField
              value={routeEdit.coordinates?.x || ''}
              onChange={e =>
                setRouteEdit(route => ({
                  ...route,
                  coordinates: { ...route.coordinates, x: +e.target.value },
                }))
              }
              size="small"
              type="number"
              label="X"
              required
            />
            {';'}
            <TextField
              value={routeEdit.coordinates?.y || ''}
              onChange={e =>
                setRouteEdit(route => ({
                  ...route,
                  coordinates: { ...route.coordinates, y: +e.target.value },
                }))
              }
              size="small"
              type="number"
              label="Y"
              required
            />
            {')'}
          </Stack>
          <div>From</div>
          <Stack direction="row" alignItems="center" spacing={0.5}>
            <TextField
              value={routeEdit.from?.name || ''}
              onChange={e =>
                setRouteEdit(route => ({
                  ...route,
                  from: {
                    ...route.from,
                    name: e.target.value,
                  },
                }))
              }
              size="small"
              label="Name"
            />
            {'('}
            <TextField
              value={routeEdit.from?.x || ''}
              onChange={e =>
                setRouteEdit(route => ({
                  ...route,
                  from: {
                    ...route.from,
                    x: +e.target.value,
                  },
                }))
              }
              size="small"
              type="number"
              label="X"
            />
            {';'}
            <TextField
              value={routeEdit.from?.y || ''}
              onChange={e =>
                setRouteEdit(route => ({
                  ...route,
                  from: {
                    ...route.from,
                    y: +e.target.value,
                  },
                }))
              }
              size="small"
              type="number"
              label="Y"
            />
            {')'}
          </Stack>
          <div>To</div>
          <Stack direction="row" alignItems="center" spacing={0.5}>
            {'('}
            <TextField
              value={routeEdit.to?.x || ''}
              onChange={e =>
                setRouteEdit(route => ({
                  ...route,
                  to: { ...route.to, x: +e.target.value },
                }))
              }
              size="small"
              type="number"
              label="X"
              required
            />
            {';'}
            <TextField
              value={routeEdit.to?.y || ''}
              onChange={e =>
                setRouteEdit(route => ({
                  ...route,
                  to: { ...route.to, y: +e.target.value },
                }))
              }
              size="small"
              type="number"
              label="Y"
              required
            />
            {';'}
            <TextField
              value={routeEdit.to?.z || ''}
              onChange={e =>
                setRouteEdit(route => ({
                  ...route,
                  to: { ...route.to, z: +e.target.value },
                }))
              }
              size="small"
              type="number"
              label="Z"
              required
            />
            {')'}
          </Stack>
          <div>Distance</div>
          <TextField
            value={routeEdit.distance || ''}
            onChange={e =>
              setRouteEdit(route => ({ ...route, distance: +e.target.value }))
            }
            fullWidth
            size="small"
            type="number"
            label="Distance"
            required
          />
          <div>Creation Date</div>
          <div>
            {routeEdit.creationDate
              ? new Date(routeEdit.creationDate).toLocaleDateString('ru')
              : new Date().toLocaleDateString('ru')}
          </div>
        </Stack>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={() => onSubmit(routeEdit)}>Submit</Button>
      </DialogActions>
    </Dialog>
  );
};
