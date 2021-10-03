import { IconButton } from '@mui/material';
import { Route } from '../../store/routes.store';
import { RoutesThunks } from '../../thunks/routes.thunks';
import { useDispatch } from '../../store/hooks';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import React from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';

type RoutesTableRowProps = {
  route: Route;
  index: number;
  onEdit: (index: number) => void;
};

export const RoutesTableRow: React.FC<RoutesTableRowProps> = ({
  route,
  index,
  onEdit,
}) => {
  const dispatch = useDispatch();

  const deleteRow = () => {
    dispatch(RoutesThunks.deleteRoute(index));
  };

  return (
    <TableRow hover>
      <TableCell size="small">{route.id}</TableCell>
      <TableCell align="right">{route.name}</TableCell>
      <TableCell align="right">{`(${route.coordinates.x};${route.coordinates.y})`}</TableCell>
      <TableCell align="right">
        {route.from
          ? `${route.from?.name}(${route.from?.x};${route.from?.y})`
          : 'Empty'}
      </TableCell>
      <TableCell align="right">{`(${route.to.x};${route.to.y};${route.to.z})`}</TableCell>
      <TableCell align="right">{route.distance}</TableCell>
      <TableCell align="right">
        {new Date(route.creationDate).toLocaleDateString('ru')}
      </TableCell>
      <TableCell align="right">
        <IconButton onClick={() => onEdit(index)} size="small">
          <EditIcon fontSize="small" />
        </IconButton>
        <IconButton onClick={deleteRow} size="small">
          <DeleteIcon fontSize="small" />
        </IconButton>
      </TableCell>
    </TableRow>
  );
};
