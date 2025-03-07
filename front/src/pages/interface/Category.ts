export enum Category {
  SPORT = "SPORT",
  ACTUALITY = "ACTUALITY",
  COUNTRIES = "COUNTRIES",
  POLITICS = "POLITICS",
  FLAGS = "FLAGS",
  ENTERTAINMENT = "ENTERTAINMENT",
  HISTORY = "HISTORY",
  SCIENCE = "SCIENCE",
  GEOGRAPHY = "GEOGRAPHY"
}
export const CategoryDisplayNames: { [key in Category]: string } = {
    [Category.SPORT]: "ספורט",
    [Category.ACTUALITY]: "אקטואליה",
    [Category.COUNTRIES]: "מדינות",
    [Category.POLITICS]: "פוליטיקה",
    [Category.FLAGS]: "דגלים",
    [Category.ENTERTAINMENT]: "בידור",
    [Category.HISTORY]: "היסטוריה",
    [Category.SCIENCE]: "מדע",
    [Category.GEOGRAPHY]: "גאוגרפיה",
  };
  