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
  creationDate: number;
  from?: LocationFrom;
  to: LocationTo;
  distance: number;
};

export type RoutePartial = {
  id?: number;
  name?: string;
  coordinates?: Partial<Coordinates>;
  from?: Partial<LocationFrom>;
  to?: Partial<LocationTo>;
  creationDate?: number | null;
  distance?: number;
};

export const emptyRoute: RoutePartial = {
  name: undefined,
  coordinates: undefined,
  to: undefined,
  creationDate: null,
  distance: undefined,
};
