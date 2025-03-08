export enum Category {
  SPORT = "SPORT",
  COUNTRIES = "COUNTRIES",
  ENTERTAINMENT = "ENTERTAINMENT",
  HISTORY = "HISTORY",
  SCIENCE = "SCIENCE",
  GEOGRAPHY = "GEOGRAPHY"
}
export const CategoryDisplayNames: { [key in Category]: string } = {
    [Category.SPORT]: "ספורט",
    [Category.COUNTRIES]: "מדינות",
    [Category.ENTERTAINMENT]: "בידור",
    [Category.HISTORY]: "היסטוריה",
    [Category.SCIENCE]: "מדע",
    [Category.GEOGRAPHY]: "גאוגרפיה",
  };
  