import * as React from 'react';
import { EditRoutePopup } from '../popups/edit-route-popup';
import { Route, RoutePartial, emptyRoute } from '../../store/routes.store';
import { RoutesApi } from '../../api/routes.api';
import { RoutesTableFooter } from './table.footer';
import { RoutesTableHeader } from './table.header';
import { RoutesTableRow } from './table.row';
import { RoutesThunks } from '../../thunks/routes.thunks';
import { partialToRoute } from '../../util/util';
import { useDispatch, useSelector } from '../../store/hooks';
import { useState } from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableContainer from '@mui/material/TableContainer';

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

type RoutesTableProps = {
  page: number;
  onPageChange: (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number,
  ) => void;
  rowsPerPage: number;
  onRowsPerPageChange: (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => void;
};

export const RoutesTable: React.FC<RoutesTableProps> = ({
  page,
  onPageChange,
  rowsPerPage,
  onRowsPerPageChange,
}) => {
  const dispatch = useDispatch();

  const { status } = useSelector(state => state.routes);

  const [editIndex, setEditIndex] = useState<number | null>();
  const [isPopupOpen, setPopupOpen] = useState(false);
  const rowToEdit = editIndex != null ? routes[editIndex] : emptyRoute;

  const onEditClick = (index: number) => {
    setEditIndex(index);
    setPopupOpen(true);
  };

  const onSubmit = (route: RoutePartial) => {
    if (editIndex != null) {
      dispatch(RoutesThunks.updateRoute(partialToRoute(route)));
    } else {
      RoutesApi.addRoute(route);
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
