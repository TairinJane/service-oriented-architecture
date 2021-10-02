import { TableCell, TableHead, TableRow } from '@mui/material';
import React from 'react';

export const RoutesTableHeader: React.FC = () => {
  return (
    <TableHead>
      <TableRow>
        <TableCell>Id</TableCell>
        <TableCell align="right">Name</TableCell>
        <TableCell align="right">Coordinates</TableCell>
        <TableCell align="right">From</TableCell>
        <TableCell align="right">To</TableCell>
        <TableCell align="right">Distance</TableCell>
      </TableRow>
    </TableHead>
  );
};
