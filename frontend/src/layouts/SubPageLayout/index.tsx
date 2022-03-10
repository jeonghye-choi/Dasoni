import { Header } from "../../components/atoms/wrapper/Header";
import * as React from "react";
import { Outlet } from "react-router";
import { Link } from "react-router-dom";

function SubPageLayout() {
  return (
    <>
      <Header>
        <Link to="/">
          <div>&lt;</div>
        </Link>
        <span className="title"></span>
      </Header>
      <main>
        <Outlet />
      </main>
    </>
  );
}

export default SubPageLayout;