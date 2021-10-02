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

export type RoutePartial = {
  id?: number;
  name?: string;
  coordinates: Partial<Coordinates>;
  from?: Partial<LocationFrom>;
  to: Partial<LocationTo>;
  creationDate: Date | null;
  distance?: number;
};

export const emptyRoute: RoutePartial = {
  name: '',
  coordinates: {},
  to: {},
  creationDate: null,
  distance: undefined,
};

type RoutesSlice = {
  entities: Route[];
  status: Status;
};

enum Status {
  IDLE,
  FETCHING,
  LOADED,
  ERROR,
}

const initialState: RoutesSlice = {
  entities: [],
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
        state.entities = action.payload;
      })
      .addCase(RoutesThunks.getRoutes.rejected, state => {
        state.status = Status.ERROR;
      }),
});

export const routesReducer = routesSlice.reducer;
