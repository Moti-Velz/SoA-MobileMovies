var express = require("express");
var router = express.Router();
const axios = require("axios");
const config = require("../config");

router.get("/most-saved-movie", async function (req, res, next) {


  try {
    
    const apiUrl = "http://127.0.0.1:5000/movies/most-saved";
    const response = await axios.get(apiUrl);
    const apiData = response.data;

    res.render("mostSavedMovie", { config: config, apiData: apiData });
  } catch (error) {
    // console.error("Error:", error);
    res.status(500).render("error", {message: "An error occurred",error});
  }
});


router.get("/global-stat", async function (req, res, next) {
  try {
    const apiUrl1 = "http://127.0.0.1:5000/users/total";
    const response1 = await axios.get(apiUrl1);
    

    const apiUrl2 = "http://127.0.0.1:5000/movies/total-saved";
    const response2 = await axios.get(apiUrl2);
    

    const apiUrl3 = "http://127.0.0.1:5000/movies/average-per-user";
    const response3 = await axios.get(apiUrl3);
    console.log(response3)

    const apiData = {
      numberOfUsers: response1.data.data.numberOfUsers,
      numberOfMovies: response2.data.data.numberOfMovies,
      globalAverageMovies: response3.data.averageMoviePerUser,
    };

    console.log(apiData);

    res.render("globalStat", { apiData: apiData });
  } catch (error) {
     // console.error("Error:", error);
     res.status(500).render("error", {message: "An error occurred",error});
  }
});

module.exports = router;
