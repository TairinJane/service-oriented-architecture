import { GetMinPopup } from '../popups/get-min-popup';
import { Route } from '../../store/routes.store';
import { RoutesApi } from '../../api/routes.api';
import Button from '@mui/material/Button';
import React, { useState } from 'react';

export const GetMinButton: React.FC = () => {
  const [isOpen, setOpen] = useState(false);
  const [route, setRoute] = useState<Route>();
  const [error, setError] = useState();

  const onSubmit = () => {
    RoutesApi.getWithMinDistance()
      .then(res => {
        setRoute(res);
      })
      .catch(error => setError(error))
      .finally(() => setOpen(true));
  };

  return (
    <>
      <Button
        variant="contained"
        color="secondary"
        fullWidth
        onClick={onSubmit}
      >
        Get Route with Min Distance
      </Button>
      <GetMinPopup
        isOpen={isOpen}
        onClose={() => setOpen(false)}
        route={route}
        error={error}
      />
    </>
  );
};
