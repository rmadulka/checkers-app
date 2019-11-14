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

      <#if currentUser??>
        <a style="text-align: center" href="/replay"> Replays </a>
      </#if>

    <#if userInGame??>
        <p>
          ${userInGame}
        </p>
    </#if>
  </div>
    <div class = "players">
      <#if currentUser??>
          <#if playersOnline??>
              <h2>Joe the AI</h2>
                  <form id="AIform" action="./game" method="GET">
                      <input type="hidden" name="AIPlayer" value="AIPlayer" />
                      <a href="javascript:;" onclick="document.getElementById('AIform').submit();">JoeTheAI</a>
                  </form>
              <h2>Players Online</h2>
              <#list playersOnline as p>
                 <#if !(p.name == currentUser.name)>
                      <form id="opponent${p.name}" action="./game" method="GET">
                          <input type="hidden" name="receiver" value="${p.name}" />
                          <a href="javascript:;" onclick="document.getElementById('opponent${p.name}').submit();"> ${p.name} </a>
                      </form>
                 </#if>
              </#list>
          </#if>
      </#if>
      <#if !currentUser??>
        <h5>${numPlayers} Players Online</h5>
      </#if>
    </div>
</div>
</body>

</html>