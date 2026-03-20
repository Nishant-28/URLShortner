# Frontend in Bun + Vite + React

Starter README for the frontend of this project.

## Tech Stack

- Bun
- Vite
- React
- TypeScript or JavaScript, depending on the app setup

## Getting Started

If this frontend is not initialized yet, create it with Bun and Vite:

```bash
bun create vite .
```

Then install dependencies:

```bash
bun install
```

Run the development server:

```bash
bun run dev
```

## Common Scripts

Typical scripts for a Bun + Vite + React app:

- `bun run dev` - start the local development server
- `bun run build` - create a production build
- `bun run preview` - preview the production build locally
- `bun run lint` - run lint checks

## Project Structure

A simple starting structure:

```text
src/
  components/
  pages/
  assets/
  App.tsx
  main.tsx
```

## Environment Variables

If the app needs environment variables, keep them in `.env` files and document the required values here.

Example:

```env
VITE_API_URL=http://localhost:3000
```

## Notes

- Update this README once the frontend structure is finalized.
- Add setup instructions, API details, and deployment notes as the app grows.
