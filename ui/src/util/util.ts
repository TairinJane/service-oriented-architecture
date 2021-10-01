export const capitalize = (str = '') =>
  str.length ? str[0].toUpperCase() + str.substring(1).toLowerCase() : str;
