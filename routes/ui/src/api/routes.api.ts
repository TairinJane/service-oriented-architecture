import { Route, RoutePartial } from '../store/routes.store';
import { RouteFields } from '../components/filter-sorter/config';
import { callApi } from '../util/util';
import { stringify } from 'query-string';

const ROUTES_API_BASE = '/api/routes';

export type QuerySort = string[];
export type QueryFilter = Partial<Record<RouteFields, any>>;

type RoutesQuery = {
  sort?: QuerySort;
  limit?: number;
  offset?: number;
  filter?: QueryFilter;
};

const getRoutes = async (query: RoutesQuery): Promise<Route[]> => {
  const queryString = stringify({
    sort: query.sort,
    limit: query.limit || 10,
    offset: query.offset || 0,
    ...query.filter,
  });
  return await callApi(`${ROUTES_API_BASE}?${queryString}`);
};

const getRouteById = async (id: number): Promise<Route> => {
  return await callApi(`${ROUTES_API_BASE}/${id}`);
};

const addRoute = async (route: RoutePartial): Promise<Route> => {
  return await callApi(`${ROUTES_API_BASE}`, {
    method: 'POST',
    body: JSON.stringify(route),
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
  });
};

const updateRoute = async (route: Route): Promise<Route> => {
  return await callApi(`${ROUTES_API_BASE}/${route.id}`, {
    method: 'PUT',
    body: JSON.stringify(route),
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
  });
};

const deleteRouteById = async (id: number): Promise<Route> => {
  return await callApi(`${ROUTES_API_BASE}/${id}`, {
    method: 'DELETE',
  });
};

const deleteWithDistance = async (distance: number): Promise<Route> => {
  return await callApi(
    `${ROUTES_API_BASE}/delete-with-distance?distance=${distance}`,
    {
      method: 'DELETE',
    },
  );
};

const countShorterThan = async (distance: number): Promise<number> => {
  return await callApi(
    `${ROUTES_API_BASE}/count-shorter-than?distance=${distance}`,
  );
};

const getWithMinDistance = async (): Promise<Route> => {
  return await callApi(`${ROUTES_API_BASE}/min-distance`);
};

export const RoutesApi = {
  getRoutes,
  getRouteById,
  addRoute,
  updateRoute,
  deleteRouteById,
  deleteWithDistance,
  countShorterThan,
  getWithMinDistance,
};
