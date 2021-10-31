import * as React from 'react';
import { Button, TableCell } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import TableFooter from '@mui/material/TableFooter';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';

type RoutesTableFooterProps = {
  page: number;
  rowsPerPage: number;
  count: number;
  hasMore: boolean;
  onPageChange: (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number,
  ) => void;
  onRowsPerPageChange: (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => void;
  onAdd: () => void;
};

export const RoutesTableFooter: React.FC<RoutesTableFooterProps> = ({
  page,
  rowsPerPage,
  count,
  hasMore,
  onPageChange,
  onRowsPerPageChange,
  onAdd,
}) => {
  return (
    <TableFooter>
      <TableRow>
        <TableCell colSpan={2}>
          <Button onClick={onAdd} startIcon={<AddIcon />}>
            Add Route
          </Button>
        </TableCell>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          colSpan={6}
          count={hasMore ? -1 : count}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={onPageChange}
          onRowsPerPageChange={onRowsPerPageChange}
          nextIconButtonProps={{ disabled: !hasMore }}
        />
      </TableRow>
    </TableFooter>
  );
};
