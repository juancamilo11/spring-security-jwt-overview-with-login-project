import { types } from "../types/types";

const initialState = {};

const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case types.login:
      return {};
    case types.logout:
      return {};
      return state;
  }
};

export default authReducer;
