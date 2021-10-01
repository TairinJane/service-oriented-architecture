import { Alert } from '@mui/material';
import { DataGrid, GridColDef, GridRowsProp } from '@mui/x-data-grid';
import React, { useCallback, useEffect, useState } from 'react';

const rows: GridRowsProp = [
  { id: 1, col1: 'Hello', col2: 'World' },
  { id: 2, col1: 'DataGridPro', col2: 'is Awesome' },
  { id: 3, col1: 'Material-UI', col2: 'is Amazing' },
];

const columns: GridColDef[] = [
  {
    field: 'col1',
    headerName: 'Column 1',
    width: 150,
    editable: true,
    sortable: false,
  },
  {
    field: 'col2',
    headerName: 'Column 2',
    width: 150,
    editable: true,
    sortable: false,
  },
];

const rowsPerPageOptions = [5, 10, 25, 50];

export const Table: React.FC = () => {
  const [pageSize, setPageSize] = useState(10);
  const [page, setPage] = useState(0);
  const [editRowsModel, setEditRowsModel] = useState({});

  const onPageChange = useCallback(() => {
    //fetch new page if needed
  }, []);

  useEffect(() => {
    onPageChange();
  }, [page]);

  const onRowEditCommit = useCallback(() => {
    //send new value on server
    console.log(editRowsModel);
  }, [rows, editRowsModel]);

  return (
    <div style={{ height: 300, width: '100%' }}>
      <DataGrid
        rows={rows}
        columns={columns}
        rowCount={100}
        rowsPerPageOptions={rowsPerPageOptions}
        paginationMode="server"
        pageSize={pageSize}
        onPageSizeChange={setPageSize}
        onPageChange={setPage}
        editMode="row"
        editRowsModel={editRowsModel}
        onEditRowsModelChange={setEditRowsModel}
        disableColumnFilter
        disableColumnMenu
        disableSelectionOnClick
        onRowEditCommit={onRowEditCommit}
      />
      <Alert severity="info" style={{ marginTop: 8 }}>
        <code>editRowsModel: {JSON.stringify(editRowsModel)}</code>
      </Alert>
    </div>
  );
};
