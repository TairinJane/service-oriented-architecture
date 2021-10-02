import * as React from 'react';
import { EditRoutePopup } from '../popups/edit-route-popup';
import { FilterSorterContext } from '../filter.context';
import { Route, RoutePartial, emptyRoute } from '../../store/routes.store';
import { RoutesApi } from '../../api/routes.api';
import { RoutesTableFooter } from './table.footer';
import { RoutesTableHeader } from './table.header';
import { RoutesTableRow } from './table.row';
import { RoutesThunks } from '../../thunks/routes.thunks';
import { partialToRoute } from '../../util/util';
import { useContext, useState } from 'react';
import { useDispatch, useSelector } from '../../store/hooks';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';

const routes: Route[] = [
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
  const dispatch = useDispatch();

  const { page, setPage, rowsPerPage, setRowsPerPage } =
    useContext(FilterSorterContext);

  const { status } = useSelector(state => state.routes);

  const [editIndex, setEditIndex] = useState<number | null>();
  const [isPopupOpen, setPopupOpen] = useState(false);
  const rowToEdit = editIndex != null ? routes[editIndex] : emptyRoute;

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

  const onEditClick = (index: number) => {
    setEditIndex(index);
    setPopupOpen(true);
  };

  const onSubmit = (route: RoutePartial) => {
    if (editIndex != null) {
      dispatch(RoutesThunks.updateRoute(partialToRoute(route)));
    } else {
      RoutesApi.addRoute(partialToRoute(route));
    }
  };

  const shownRows = routes.slice(
    page * rowsPerPage,
    page * rowsPerPage + rowsPerPage,
  );

  return (
    <>
      <TableContainer component={Paper}>
        <Table
          sx={{ minWidth: 500 }}
          aria-label="routes table"
          style={{ tableLayout: 'auto' }}
          size="small"
        >
          <RoutesTableHeader />
          <TableBody>
            {shownRows.map((row, index) => (
              <RoutesTableRow
                route={row}
                key={index}
                index={index}
                onEdit={onEditClick}
              />
            ))}
          </TableBody>
          <RoutesTableFooter
            page={page}
            rowsPerPage={rowsPerPage}
            onPageChange={onPageChange}
            onRowsPerPageChange={onRowsPerPageChange}
            rowsCount={routes.length}
            onAdd={() => setPopupOpen(true)}
          />
        </Table>
      </TableContainer>
      {isPopupOpen && (
        <EditRoutePopup
          route={rowToEdit}
          isOpen={isPopupOpen}
          onSubmit={onSubmit}
          onClose={() => {
            setEditIndex(null);
            setPopupOpen(false);
          }}
        />
      )}
    </>
  );
};
