import * as React from 'react';
import { EditRoutePopup } from '../popups/edit-route-popup';
import { EmptyRow } from './empty-row';
import { RoutePartial, emptyRoute } from '../../store/routes.store';
import { RoutesApi } from '../../api/routes.api';
import { RoutesTableFooter } from './table.footer';
import { RoutesTableHeader } from './table.header';
import { RoutesTableRow } from './table.row';
import { RoutesThunks } from '../../thunks/routes.thunks';
import { Status } from '../../store/routes.slice';
import { TableCell, TableRow } from '@mui/material';
import { partialToRoute } from '../../util/util';
import { useDispatch, useSelector } from '../../store/hooks';
import { useSnackbar } from 'notistack';
import { useState } from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableContainer from '@mui/material/TableContainer';

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
  const { enqueueSnackbar } = useSnackbar();

  const {
    status,
    entities: routes,
    hasMore,
  } = useSelector(state => state.routes);

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
      RoutesApi.addRoute(route)
        .then(route => {
          enqueueSnackbar(`Route was added with id = ${route.id}`, {
            variant: 'success',
          });
          setPopupOpen(false);
        })
        .catch(err => enqueueSnackbar(String(err), { variant: 'error' }));
    }
  };

  const shownRows = routes.slice(
    page * rowsPerPage,
    page * rowsPerPage + rowsPerPage,
  );

  const nextPageAvailable =
    page * rowsPerPage + rowsPerPage < routes.length || hasMore;

  return (
    <>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 500 }} aria-label="routes table" size="small">
          <RoutesTableHeader />
          <TableBody>
            {shownRows.map(route => (
              <RoutesTableRow
                route={route}
                key={route.id}
                index={route.id || 0}
                onEdit={onEditClick}
              />
            ))}
            {status == Status.LOADED && !routes.length && (
              <TableRow>
                <TableCell colSpan={8} align="center">
                  No Routes found
                </TableCell>
              </TableRow>
            )}
            {shownRows.length < rowsPerPage && (
              <EmptyRow
                rowSpan={rowsPerPage - shownRows.length}
                colSpan={8}
                text={status == Status.FETCHING ? 'Loading...' : ''}
              />
            )}
          </TableBody>
          <RoutesTableFooter
            page={page}
            rowsPerPage={rowsPerPage}
            onPageChange={onPageChange}
            onRowsPerPageChange={onRowsPerPageChange}
            onAdd={() => setPopupOpen(true)}
            hasMore={nextPageAvailable}
            count={routes.length}
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
