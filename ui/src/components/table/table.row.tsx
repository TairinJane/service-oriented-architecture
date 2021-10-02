import { Route } from '../../store/routes.slice';
import React from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';

type RoutesTableRowProps = {
  route: Route;
  index: number;
  isEditing?: boolean;
};

export const RoutesTableRow: React.FC<RoutesTableRowProps> = ({
  route,
  index,
  isEditing,
}) => {
  return (
    <TableRow hover>
      <TableCell>{route.id}</TableCell>
      <TableCell align="right">{route.name}</TableCell>
      <TableCell align="right">{`(${route.coordinates.x};${route.coordinates.y})`}</TableCell>
      <TableCell align="right">
        {route.from
          ? `${route.from?.name}(${route.from?.x};${route.from?.y})`
          : 'Empty'}
      </TableCell>
      <TableCell align="right">{`(${route.to.x};${route.to.y};${route.to.z})`}</TableCell>
      <TableCell align="right">{route.distance}</TableCell>
    </TableRow>
  );
};
