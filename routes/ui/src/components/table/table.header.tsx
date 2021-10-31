import {
  TableCell,
  TableHead,
  TableRow,
  tableCellClasses,
} from '@mui/material';
import { styled } from '@mui/styles';
import React from 'react';

const HeaderTableCell = styled(TableCell)(() => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: '#42a5f5',
    color: 'white',
    fontWeight: 'bold',
  },
}));

export const RoutesTableHeader: React.FC = () => {
  return (
    <TableHead>
      <TableRow>
        <HeaderTableCell size="small" width={10}>
          Id
        </HeaderTableCell>
        <HeaderTableCell align="right">Name</HeaderTableCell>
        <HeaderTableCell align="right">Coordinates (X;Y)</HeaderTableCell>
        <HeaderTableCell align="right">From Name(X;Y)</HeaderTableCell>
        <HeaderTableCell align="right">To (X;Y;Z)</HeaderTableCell>
        <HeaderTableCell align="right">Distance</HeaderTableCell>
        <HeaderTableCell align="right">Creation Date</HeaderTableCell>
        <HeaderTableCell align="right" />
      </TableRow>
    </TableHead>
  );
};
