import { Route, RoutePartial } from '../store/routes.store';

export const capitalize = (str = '') =>
  str.length ? str[0].toUpperCase() + str.substring(1).toLowerCase() : str;

export const partialToRoute = (partial: RoutePartial): Route => {
  return partial as Route;
};
