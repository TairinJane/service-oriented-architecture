import { RoutesApi } from '../api/routes.api';
import { createAsyncThunk } from '@reduxjs/toolkit';

const ROUTES_THUNKS_BASE = 'routes/';

const getRoutes = createAsyncThunk(
  ROUTES_THUNKS_BASE + 'get',
  RoutesApi.getRoutes,
);

const updateRoute = createAsyncThunk(
  ROUTES_THUNKS_BASE + 'update',
  RoutesApi.updateRoute,
);

const deleteRoute = createAsyncThunk(
  ROUTES_THUNKS_BASE + 'delete',
  RoutesApi.deleteRouteById,
);

export const RoutesThunks = {
  getRoutes,
  updateRoute,
  deleteRoute,
};
