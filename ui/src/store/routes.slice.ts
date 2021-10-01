import { RoutesThunks } from '../thunks/routes.thunks';
import { createSlice } from '@reduxjs/toolkit';

export type Coordinates = {
  id?: number;
  x: number;
  y: number;
};

export type LocationFrom = {
  id?: number;
  x: number;
  y: number;
  name?: string;
};

export type LocationTo = {
  id?: number;
  x: number;
  y: number;
  z: number;
};

export type Route = {
  id?: number;
  name: string;
  coordinates: Coordinates;
  creationDate: Date;
  from?: LocationFrom;
  to: LocationTo;
  distance: number;
};

type RoutesSlice = {
  routes: Route[];
  status: Status;
};

enum Status {
  IDLE,
  FETCHING,
  LOADED,
  ERROR,
}

const initialState: RoutesSlice = {
  routes: [],
  status: Status.IDLE,
};

export const routesSlice = createSlice({
  name: 'routes',
  initialState,
  reducers: {},
  extraReducers: builder =>
    builder
      .addCase(RoutesThunks.getRoutes.pending, state => {
        state.status = Status.FETCHING;
      })
      .addCase(RoutesThunks.getRoutes.fulfilled, (state, action) => {
        state.status = Status.LOADED;
        state.routes = action.payload;
      })
      .addCase(RoutesThunks.getRoutes.rejected, state => {
        state.status = Status.ERROR;
      }),
});

export const routesReducer = routesSlice.reducer;
