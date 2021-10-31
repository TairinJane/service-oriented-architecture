import { CountShorterButton } from './count-shorter-button';
import { DeleteWithDistanceButton } from './delete-with-distance-button';
import { GetByIdButton } from './get-by-id-button';
import { GetMinButton } from './get-min-button';
import { Stack } from '@mui/material';
import React from 'react';

export const AdditionalActions: React.FC = () => {
  return (
    <>
      <Stack spacing={1} direction="row">
        <GetByIdButton />
        <DeleteWithDistanceButton />
        <CountShorterButton />
        <GetMinButton />
      </Stack>
    </>
  );
};
