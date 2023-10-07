var express = require("express");
var router = express.Router();
const axios = require("axios");
const config = require("../config");


router.get("/", function (req, res, next) {
  res.render("index", { title: "Express" });
});

router.get("/users", async function (req, res, next) {
  try {
    const apiUrl = "http://localhost:3002/users";
    const response = await axios.get(apiUrl);
    const apiData = response.data;

    res.render("userList", { apiData });
  } catch (error) {
     // console.error("Error:", error);
     res.status(500).render("error", {message: "An error occurred",error});
  }
});

router.get("/user/:userId", async function (req, res, next) {
  const { userId } = req.params;

  try {
    const apiUrl = "http://localhost:3002/users/" + userId;
    const response = await axios.get(apiUrl);
    const apiData = response.data;

    res.render("userDetails", { apiData });
  } catch (error) {
    // console.error("Error:", error);
    res.status(500).render("error", {message: "An error occurred",error});
  }
});


router.get("/user/:username/movies", async function (req, res, next) {
  const { username } = req.params;

  try {
    const apiUrl = "http://localhost:3002/users/" + username + "/movies";
    const response = await axios.get(apiUrl, { validateStatus: false });
    const apiData = response.data;

    if (response.status == 404) {
      
      return res.status(404).send("User or Movie not found")
    }

    if (response.status == 200) {
      
      return res.render("movieListFromUser", { apiData, config });
    }

    
    return res.status(500).send("An error occurred")
  } catch (error) {
   
    console.error("Error:", error);
    res.status(500).render("error", { message: "An error occurred", error });
  }
});




module.exports = router;
