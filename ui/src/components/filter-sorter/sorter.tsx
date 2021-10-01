import './sorter.css';
import { Alert, Grid, Paper, TextField } from '@mui/material';
import { DatePicker, LocalizationProvider } from '@mui/lab';
import {
  RouteFields,
  SortType,
  getFieldEndLabel,
  getFieldLabel,
  initialSorting,
} from './config';
import { SortingInput } from './sorting-input';
import DateAdapter from '@mui/lab/AdapterDateFns';
import React, { useState } from 'react';

export const Sorter: React.FC = () => {
  const [filterSorter, setFilterSorter] = useState(initialSorting);

  const setFieldSorting = (field: RouteFields, sorting: SortType) => {
    setFilterSorter({
      ...filterSorter,
      [field]: { ...filterSorter[field], sorting },
    });
  };

  const setFieldValue = (field: RouteFields, value: string) => {
    setFilterSorter({
      ...filterSorter,
      [field]: { ...filterSorter[field], value },
    });
  };

  return (
    <Paper variant="outlined" className="sorter">
      <Grid container spacing={1} direction="column">
        <Grid item xs={12}>
          <div className="section-label">{getFieldLabel(RouteFields.ID)}</div>
          <Grid container spacing={1}>
            <Grid item xs>
              <TextField
                label={getFieldLabel(RouteFields.ID)}
                value={filterSorter[RouteFields.ID].value}
                onChange={e => setFieldValue(RouteFields.ID, e.target.value)}
                fullWidth
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.ID].sorting}
                field={RouteFields.ID}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <div className="section-label">{getFieldLabel(RouteFields.NAME)}</div>
          <Grid container spacing={1}>
            <Grid item xs>
              <TextField
                label={getFieldLabel(RouteFields.NAME)}
                value={filterSorter[RouteFields.NAME].value}
                onChange={e => setFieldValue(RouteFields.NAME, e.target.value)}
                fullWidth
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.NAME].sorting}
                field={RouteFields.NAME}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <div className="section-label">
            {getFieldLabel(RouteFields.COORDINATES_X)}
          </div>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.COORDINATES_X)}
                value={filterSorter[RouteFields.COORDINATES_X].value}
                onChange={e =>
                  setFieldValue(RouteFields.COORDINATES_X, e.target.value)
                }
                fullWidth
                type="number"
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.COORDINATES_X].sorting}
                field={RouteFields.COORDINATES_X}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.COORDINATES_Y)}
                value={filterSorter[RouteFields.COORDINATES_Y].value}
                onChange={e =>
                  setFieldValue(RouteFields.COORDINATES_Y, e.target.value)
                }
                fullWidth
                type="number"
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.COORDINATES_Y].sorting}
                field={RouteFields.COORDINATES_Y}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <div className="section-label">{RouteFields.CREATION_DATE}</div>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <LocalizationProvider dateAdapter={DateAdapter}>
                <DatePicker
                  value={
                    filterSorter[RouteFields.CREATION_DATE].value
                      ? new Date(filterSorter[RouteFields.CREATION_DATE].value)
                      : null
                  }
                  onChange={value =>
                    setFieldValue(
                      RouteFields.CREATION_DATE,
                      value?.toISOString() || '',
                    )
                  }
                  renderInput={params => (
                    <TextField fullWidth size="small" {...params} />
                  )}
                  label={RouteFields.CREATION_DATE}
                />
              </LocalizationProvider>
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.CREATION_DATE].sorting}
                field={RouteFields.CREATION_DATE}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <div className="section-label">
            {getFieldLabel(RouteFields.FROM_X)}
          </div>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.FROM_X)}
                value={filterSorter[RouteFields.FROM_X].value}
                onChange={e =>
                  setFieldValue(RouteFields.FROM_X, e.target.value)
                }
                fullWidth
                type="number"
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.FROM_X].sorting}
                field={RouteFields.FROM_X}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.FROM_Y)}
                value={filterSorter[RouteFields.FROM_Y].value}
                onChange={e =>
                  setFieldValue(RouteFields.FROM_Y, e.target.value)
                }
                fullWidth
                type="number"
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.FROM_Y].sorting}
                field={RouteFields.FROM_Y}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.FROM_NAME)}
                value={filterSorter[RouteFields.FROM_NAME].value}
                onChange={e =>
                  setFieldValue(RouteFields.FROM_NAME, e.target.value)
                }
                fullWidth
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.FROM_NAME].sorting}
                field={RouteFields.FROM_NAME}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <div className="section-label">{getFieldLabel(RouteFields.TO_X)}</div>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.TO_X)}
                value={filterSorter[RouteFields.TO_X].value}
                onChange={e => setFieldValue(RouteFields.TO_X, e.target.value)}
                fullWidth
                type="number"
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.TO_X].sorting}
                field={RouteFields.TO_X}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.TO_Y)}
                value={filterSorter[RouteFields.TO_Y].value}
                onChange={e => setFieldValue(RouteFields.TO_Y, e.target.value)}
                fullWidth
                type="number"
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.TO_Y].sorting}
                field={RouteFields.TO_Y}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.TO_Z)}
                value={filterSorter[RouteFields.TO_Z].value}
                onChange={e => setFieldValue(RouteFields.TO_Z, e.target.value)}
                fullWidth
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.TO_Z].sorting}
                field={RouteFields.TO_Z}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12}>
          <div className="section-label">
            {getFieldLabel(RouteFields.DISTANCE)}
          </div>
          <Grid container spacing={1} alignItems="center">
            <Grid item xs>
              <TextField
                label={getFieldEndLabel(RouteFields.DISTANCE)}
                value={filterSorter[RouteFields.DISTANCE].value}
                onChange={e =>
                  setFieldValue(RouteFields.DISTANCE, e.target.value)
                }
                fullWidth
                type="number"
                size="small"
              />
            </Grid>
            <Grid item xs={4}>
              <SortingInput
                sorting={filterSorter[RouteFields.DISTANCE].sorting}
                field={RouteFields.DISTANCE}
                onChange={setFieldSorting}
              />
            </Grid>
          </Grid>
        </Grid>
      </Grid>
      <Alert severity="info" style={{ marginTop: 8 }}>
        <code>sorting: {JSON.stringify(filterSorter)}</code>
      </Alert>
    </Paper>
  );
};
