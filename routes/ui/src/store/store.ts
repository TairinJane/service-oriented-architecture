import { Action, ThunkAction, configureStore } from '@reduxjs/toolkit';
import { routesReducer } from './routes.slice';
import counterReducer from './counter.slice';

export const store = configureStore({
  reducer: {
    counter: counterReducer,
    routes: routesReducer,
  },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
