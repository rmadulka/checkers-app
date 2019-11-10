<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />
    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

    <#if userInGame??>
        <p>
          ${userInGame}
        </p>
    </#if>
  </div>
  <!-- create a <ul> for players online -->
    <div class = "players">
      <#if currentUser??>
          <#if playersOnline??>
              <a href="/replay" onclick="getReplayScreen()"> Replays </a>
              <h2 style="padding-left: 10px">Players Online</h2>
              <ul>
                <#list playersOnline as p>
                    <#if !(p.name == currentUser.name)>
                        <li>
                            <form id="form" action="./game" method="GET">
                                <input type="hidden" name="receiver" value="${p.name}" />
                                <a href="javascript:;" onclick="document.getElementById('form').submit();"> [${p.name}] </a>
                            </form>
                        </li>
                    </#if>
                </#list>
              </ul>
          </#if>
      </#if>
      <#if !currentUser??>
        <h5>${numPlayers} Players Online</h5>
      </#if>
    </div>
</div>
</body>

</html>
<script>
    function getReplayScreen() {
        window.location = '/replay';
    }
</script>
