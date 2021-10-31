import {
  TypedUseSelectorHook,
  useDispatch as useBaseDispatch,
  useSelector as useBaseSelector,
} from 'react-redux';
import type { AppDispatch, RootState } from './store';

// Use throughout your app instead of plain `useDispatch` and `useSelector`
export const useDispatch = () => useBaseDispatch<AppDispatch>();
export const useSelector: TypedUseSelectorHook<RootState> = useBaseSelector;
