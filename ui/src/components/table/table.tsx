import * as React from 'react';
import { Route } from '../../store/routes.slice';
import { RoutesTableFooter } from './table.footer';
import { RoutesTableHeader } from './table.header';
import { RoutesTableRow } from './table.row';
import { useState } from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';

const rows: Route[] = [
  {
    id: 1,
    name: 'R1',
    from: { x: 1, y: 2, name: 'From1', id: 1 },
    coordinates: { x: 6, y: 8, id: 4 },
    to: { x: 6, y: 8, z: 7, id: 15 },
    creationDate: new Date(),
    distance: 16,
  },
  {
    id: 9,
    name: 'R9',
    from: { x: 1, y: 2, name: 'From0', id: 1 },
    coordinates: { x: 6.888, y: 8, id: 4 },
    to: { x: 6, y: 8, z: 7.085, id: 15 },
    creationDate: new Date(),
    distance: 16.94,
  },
];

export const RoutesTable = () => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

  const onPageChange = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number,
  ) => {
    setPage(newPage);
  };

  const onRowsPerPageChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const shownRows = rows.slice(
    page * rowsPerPage,
    page * rowsPerPage + rowsPerPage,
  );

  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 500 }} aria-label="routes table">
        <RoutesTableHeader />
        <TableBody>
          {shownRows.map((row, index) => (
            <RoutesTableRow
              route={row}
              key={index}
              index={index}
              isEditing={false}
            />
          ))}
          {emptyRows > 0 && (
            <TableRow style={{ height: 53 * emptyRows }}>
              <TableCell colSpan={6} />
            </TableRow>
          )}
        </TableBody>
        <RoutesTableFooter
          page={page}
          rowsPerPage={rowsPerPage}
          onPageChange={onPageChange}
          onRowsPerPageChange={onRowsPerPageChange}
          rowsCount={rows.length}
        />
      </Table>
    </TableContainer>
  );
};
