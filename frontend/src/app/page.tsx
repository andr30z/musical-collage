import Link from "next/link";
import type { IconType } from "react-icons";
import { AiFillGithub } from "react-icons/ai";
import { BiLogoDeezer, BiLogoSpotify } from "react-icons/bi";
import { FaLastfm } from "react-icons/fa";

function PlatformItem({
  icon: Icon,
  text,
  className = "",
  disabled,
}: {
  icon: IconType;
  text: string;
  disabled?: boolean;
  className?: string;
}) {
  return (
    <Link
      href={"/" + text.toLowerCase()}
      className={
        "rounded-full bg-black p-2 flex items-center gap-1 bg-opacity-40 hover:bg-opacity-80 " +
        className
      }
      aria-disabled={disabled || false}
    >
      <Icon size={25} />
      <span className="text-lg font-semibold">{text}</span>
    </Link>
  );
}

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <div>
        <h1 className="p-2 font-extrabold text-6xl text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-emerald-400">
          Collagify
        </h1>
      </div>

      <div className="gap-2 flex flex-col">
        <h6 className="font-extrabold text-xl">
          Generate Collages based on your favorite platform
        </h6>
        <div
          className="flex flex-wrap gap-2 items-center flex-row justify-center 
         relative before:absolute 
         after:absolute after:-z-20 after:h-[250px] after:w-[480px]
         after:translate-x-5 after:bg-gradient-to-bl after:from-blue-400 after:via-emerald-400 after:blur-2xl after:content-[''] 
         after:dark:from-emerald-400 after:dark:via-blue-400 after:dark:opacity-40 z-[-1]"
        />
        <div className="flex flex-wrap gap-3 items-center flex-row justify-center ">
          <PlatformItem
            icon={BiLogoDeezer}
            text="Deezer"
            className="text-[#00c7f2]"
            disabled
          />
          <PlatformItem
            icon={BiLogoSpotify}
            text="Spotify"
            className="text-[#1DB954]"
          />
          <PlatformItem
            icon={FaLastfm}
            text="LastFM"
            className="text-[#D51007]"
          />
        </div>
      </div>
      <footer>
        <a
          className="flex items-center flex-row gap-1 hover:opacity-50"
          href="https://github.com/andr30z/collagify"
          target="_blank"
        >
          <AiFillGithub className="text-2xl" /> @andr30z
        </a>
      </footer>
    </main>
  );
}
