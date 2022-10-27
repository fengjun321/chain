// 公共方法
export const getData = (data = {}) => {
  return {
    code: 200,
    data,
    message: null,
  };
};


export const getRecords = (records = [], total = 0) => {
  return {
    code: 200,
    data: { records },
    total,
    message: null,
  };
};
