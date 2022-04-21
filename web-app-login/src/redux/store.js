import {
  createStore,
  applyMiddleware,
  combineReducers,
  compose,
  combineReducers,
} from "redux";
import thunk from "redux-thunk";
import authReducer from "./reducers/authReducer";

const composeEnhancers =
  (typeof window !== "undefined" &&
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__) ||
  compose;

const reducers = combineReducers({
  auth: authReducer,
});

const store = createStore(reducers, composeEnhancers(applyMiddleware(thunk)));

export default store;
