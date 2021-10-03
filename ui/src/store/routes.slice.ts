import { Route } from './routes.store';
import { RoutesThunks } from '../thunks/routes.thunks';
import { createSlice } from '@reduxjs/toolkit';

type RoutesSlice = {
  entities: Route[];
  status: Status;
  hasMore: boolean;
};

export enum Status {
  IDLE,
  FETCHING,
  LOADED,
  ERROR,
}

const initialState: RoutesSlice = {
  entities: [],
  status: Status.IDLE,
  hasMore: true,
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
        const { arg: query } = action.meta;
        if (query.offset == 0) {
          state.entities = action.payload;
          state.hasMore = true;
        } else if (state.entities.length == query.offset) {
          state.entities = state.entities.concat(action.payload);
        }
        if (action.payload.length < (query.limit || 5)) state.hasMore = false;
        console.log({ query, hasMore: state.hasMore });
      })
      .addCase(RoutesThunks.getRoutes.rejected, state => {
        state.status = Status.ERROR;
      })
      .addCase(RoutesThunks.updateRoute.pending, state => {
        state.status = Status.FETCHING;
      })
      .addCase(RoutesThunks.updateRoute.fulfilled, (state, action) => {
        state.status = Status.LOADED;
        state.entities = state.entities.concat(action.payload);
      })
      .addCase(RoutesThunks.updateRoute.rejected, state => {
        state.status = Status.ERROR;
      })
      .addCase(RoutesThunks.deleteRoute.pending, state => {
        state.status = Status.FETCHING;
      })
      .addCase(RoutesThunks.deleteRoute.fulfilled, (state, action) => {
        state.status = Status.LOADED;
        const deletedIndex = state.entities.findIndex(
          route => route.id == action.payload.id,
        );
        state.entities = state.entities.splice(deletedIndex, 1);
      })
      .addCase(RoutesThunks.deleteRoute.rejected, state => {
        state.status = Status.ERROR;
      }),
});

export const routesReducer = routesSlice.reducer;
