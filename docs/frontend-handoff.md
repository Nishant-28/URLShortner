# Frontend Handoff Guide

This is the practical checklist for the frontend team.

## What the backend already provides

- registration flow
- login via HTTP Basic
- public short-link listing
- create-link flow
- my-links page data
- delete-selected-links action
- redirect route for short keys
- admin dashboard data

## What the frontend should own

- UI/UX for authentication
- form validation feedback
- empty states and loading states
- pagination controls
- link creation dialogs or pages
- confirmation for delete actions
- admin dashboard presentation

## Suggested pages

- landing page with public links
- register page
- login page
- create short link page or modal
- my links page
- admin dashboard page
- error page for auth or API failures

## Suggested integration pattern

- keep API base URL in an env variable
- centralize auth headers in one client module
- normalize backend `PagedResult` into frontend table state
- treat redirect URL navigation as backend-owned

## Backend quirks to account for

- some successful actions return plain text instead of JSON
- delete endpoint expects an array of numeric ids in the request body
- route protection is based on server-side auth, not frontend-only guards
- public and protected views can share the same backend host

## Coordination requests from backend

- finalize a shared API schema before adding more endpoints
- agree on response envelope conventions
- align on date display format
- define consistent empty/error/loading states
- confirm whether the frontend will use basic auth directly or proxy auth through another layer
